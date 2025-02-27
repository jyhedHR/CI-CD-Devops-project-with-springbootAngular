pipeline {

 agent any

 environment {
         JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
         M2_HOME = "/opt/apache-maven-3.6.3"
         PATH = "$M2_HOME/bin:$PATH"
         DOCKER_IMAGE = "skierDevops"
         DOCKER_TAG = "latest"
          NEXUS_VERSION = "nexus3"
                 NEXUS_PROTOCOL = "http"
                 NEXUS_URL = "172.25.251.16:8080"
                 NEXUS_REPOSITORY = "maven-central-repository"
                 NEXUS_CREDENTIAL_ID = "nexus"


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
          }
          stage("Publish to Nexus Repository Manager") {
                     steps {
                         script {
                             pom = readMavenPom file: "pom.xml";
                             filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                             echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                             artifactPath = filesByGlob[0].path;
                             artifactExists = fileExists artifactPath;
                             if(artifactExists) {
                                 echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                 nexusArtifactUploader(
                                     nexusVersion: NEXUS_VERSION,
                                     protocol: NEXUS_PROTOCOL,
                                     nexusUrl: NEXUS_URL,
                                     groupId: pom.groupId,
                                     version: pom.version,
                                     repository: NEXUS_REPOSITORY,
                                     credentialsId: NEXUS_CREDENTIAL_ID,
                                     artifacts: [
                                         [artifactId: pom.artifactId,
                                         classifier: '',
                                         file: artifactPath,
                                         type: pom.packaging],
                                         [artifactId: pom.artifactId,
                                         classifier: '',
                                         file: "pom.xml",
                                         type: "pom"]
                                     ]
                                 );
                             } else {
                                 error "*** File: ${artifactPath}, could not be found";
                             }
                         }
                     }
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
            withCredentials([string(credentialsId: 'DockerHub', variable: 'DOCKER_ACCESS_TOKEN')]) {
                sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u eyanehdi --password-stdin'
            sh 'docker tag eyanehdi:latest eyanehdi/$DOCKER_IMAGE:$DOCKER_TAG'
                sh 'docker push eyanehdi/$DOCKER_IMAGE:$DOCKER_TAG'
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