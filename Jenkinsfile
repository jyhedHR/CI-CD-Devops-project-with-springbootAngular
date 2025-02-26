pipeline {

 agent any

 environment {
         JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
         M2_HOME = "/opt/apache-maven-3.6.3"
         PATH = "$M2_HOME/bin:$PATH"
         DOCKER_IMAGE = "eyanehdi"
         DOCKER_TAG = "latest"

     }

 stages {

 stage('GIT') {

           steps {

               git branch: 'NehdiEya_4TWIN5_Groupe2',

               url: 'git@github.com:jyhedHR/4twin5_Group2_gestion-station-skier.git'

          }

     }

 stage ('Compile Stage') {

    steps {

    sh 'mvn clean compile'

    }

 }
    stage('Test Stage') {
             steps {
                 sh 'mvn -X test'
             }
      }
      stage ('Generate jar ') {

          steps {

          sh 'mvn package -DskipTests'

          }
        stage ('SonarQube analysis') {
            steps{
             withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar '
        }
    }
}
stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }
        stage('Push Docker Image') {
            steps {
            withCredentials([string(credentialsId: 'docker-hub-credentials', variable: 'DOCKER_PASSWORD')]) {
                sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                sh 'docker push $DOCKER_IMAGE:$DOCKER_TAG'
            }
            }
        }
        stage('Deploy Container') {
                    steps {
                        sh 'docker stop skiReg || true'
                        sh 'docker rm skiReg || true'
                        sh 'docker run -d --name skiReg -p 8089:8089 $DOCKER_IMAGE:$DOCKER_TAG'
                    }
                }

}


}