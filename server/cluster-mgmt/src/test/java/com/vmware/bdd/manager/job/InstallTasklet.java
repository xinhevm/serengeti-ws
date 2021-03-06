/***************************************************************************
 * Copyright (c) 2012-2013 VMware, Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package com.vmware.bdd.manager.job;

import org.apache.log4j.Logger;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.vmware.bdd.service.job.JobConstants;
import com.vmware.bdd.service.job.JobExecutionStatusHolder;
import com.vmware.bdd.service.job.TrackableTasklet;

public class InstallTasklet extends TrackableTasklet {
   private static final Logger logger = Logger.getLogger(InstallTasklet.class);

   @Override
   public RepeatStatus executeStep(ChunkContext chunkContext,
         JobExecutionStatusHolder jobExecutionStatusHolder) throws Exception {
      String nodeName =
            (String) chunkContext.getStepContext().getJobParameters()
                  .get(JobConstants.SUB_JOB_NODE_NAME);
      if (nodeName != null) {
         logger.info("install software on node: " + nodeName);
         if (nodeName.equals("node-fail-forever")) {
            throw new Exception("install software failed on node: " + nodeName);
         }
      }
      logger.info("installing softwares");
      putIntoJobExecutionContext(chunkContext,
            "some-variable-which-need-be-saved", 20);
      jobExecutionStatusHolder.setCurrentStepProgress(
            getJobExecutionId(chunkContext), 0.5);
      Thread.sleep(500);
      return RepeatStatus.FINISHED;
   }
}