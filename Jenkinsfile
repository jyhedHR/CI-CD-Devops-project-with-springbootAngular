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

        stage('Clean & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('JUnit Test') {
            steps {
                script {
                    sh 'mvn test -Dtest=InstructorServicesImplTest'
                }
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Package JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build and Push [Docker]') {
            steps {
                script {
                    sh 'docker build -t gestion-station-ski-instructor .'

                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USER" --password-stdin
                    docker tag gestion-station-ski-instructor "$DOCKER_USER/gestion-station-ski-instructor:latest"
                    docker push "$DOCKER_USER/gestion-station-ski-instructor:latest"
                '''
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.yml up -d'
                    sh 'docker-compose -f docker-compose.yml logs -f'
                }
            }
        }
    }
}
