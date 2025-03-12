pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "gestion-station-ski"
        DOCKER_TAG = "latest"
    }

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'salut Yasmine Bouteraa'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Yasmine_bouteraa_4TWIN5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'git'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }



        stage('Generate jar') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running JUnit Tests...'
                sh 'mvn test'
            }
        }


stage('Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }



    }
}
