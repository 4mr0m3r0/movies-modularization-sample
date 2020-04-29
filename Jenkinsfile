pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''echo "Assembling"
sh \'./gradlew clean assemble$FLAVOR$BUILD_TYPE\'
'''
      }
    }

  }
}