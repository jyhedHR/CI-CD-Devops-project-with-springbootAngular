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
        stage('GIT') {
            steps {
                script {
                    git branch: 'Elyess_BenSassi_4TWIN5_Group2',
                    credentialsId: 'PAT_jenkins',
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

        stage('Maven SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Maven Package JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t gestion-station-ski-instructor .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d -p 8089:8089 --name gestion-station-ski-instructor gestion-station-ski-instructor'
            }
        }
    }
}
