/*-
 * #%L
 * common
 * %%
 * Copyright (C) 2019 Buffalo Coders
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

#!groovy

pipeline {

  agent any

  stages {

    stage ('Preparation') {
      steps {
        checkout scm
        withMaven(maven: 'default') {
          sh "mvn clean"
        }
      }
    }

    stage ('Unit Test') {
      steps {
        withMaven(maven: 'default') {
          sh "mvn install"
        }
      }
    }

    stage ('Post Build Cleanup') {
      steps {
        withMaven(maven: 'default') {
          sh "mvn clean"
        }
      }
    }

  }

}
