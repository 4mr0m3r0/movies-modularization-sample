pipeline {

    options {
        timeout(time: 60, unit: 'MINUTES')
    }

    tools {
        jdk 'Oracle JDK 1.8'
    }

    parameters {
        string(name: 'FLAVOR', defaultValue: 'Dev', description: 'Build Flavor')
        string(name: 'BUILD_TYPE', defaultValue: 'Debug', description: 'Build env')
    }

    environment {
        FLAVOR = "${params.FLAVOR}"
        BUILD_TYPE = "${params.BUILD_TYPE}"
        DEBUG_TYPE = "Debug"
    }

    stages {

        stage('Build') {

            steps {
                echo "Starting printing of artifacts"
                sh "./gradlew clean assemble$FLAVOR$BUILD_TYPE"
            }

        }

    }
}
