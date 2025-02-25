
pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Hi Yassmine bouterra'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Yasmine_bouteraa_4TWIN5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'Git'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=IPisteServicesTest clean test'
             }
        }


    }
}
