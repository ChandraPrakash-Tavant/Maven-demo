pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'chandraprakashtavant'
        IMAGE_NAME     = "${DOCKERHUB_USER}/sample-maven-app"
        IMAGE_TAG      = "1.0.${BUILD_NUMBER}"
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                                                  usernameVariable: 'DH_USER',
                                                  passwordVariable: 'DH_PASS')]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest
                    '''
                }
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/app.jar'
            echo "Pushed $IMAGE_NAME:$IMAGE_TAG"
        }
        always {
            sh 'docker logout || true'
            sh 'docker rmi $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest || true'
        }
    }
}
