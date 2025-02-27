
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



        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests '
            }
        }
    }
}