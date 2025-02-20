
pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Hi Jihed'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'f/jyhed',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'Gitttt'
            }
        }

        stage('Clean install') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
