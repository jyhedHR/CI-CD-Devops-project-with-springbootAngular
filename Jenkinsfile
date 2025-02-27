pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64/'
        M2_HOME = 'opt/apache-maven-3.6.3'
    }

    tools {
            git 'Default Git'
    }

    stages {
        stage('Verify Git Installation') {
                steps {
                    script {
                        sh 'which git'  // Check Git path
                        sh 'git --version'  // Display Git version
                    }
                }
         }
        stage('GIT Checkout') {
            steps {
                script {
                    git branch: 'Elyess_BenSassi_4TWIN5_Group2', 
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git'
                }
            }
        }

        stage('Maven Clean & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Maven Test') {
            steps {
                script {
                    sh 'mvn test -Dtest=InstructorServicesImplTest'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }
}
