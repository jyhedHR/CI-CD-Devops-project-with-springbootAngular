
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
                git branch: 'Jihed_Horchani_4twin5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'Gitttt'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'

            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=SkierServicesImplTest clean test'
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