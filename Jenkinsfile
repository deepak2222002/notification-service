pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t notification-service .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop notification-service || true
                    docker rm notification-service || true

                    docker run -d \
                    --name notification-service \
                    --network backend_default \
                    --restart unless-stopped \
                    -p 8093:8443 \
                    -e ACTIVATION_URL=https://192.168.31.184:8090/activate/activateAccount \
                    -e KAFKA_BOOTSTRAP_SERVERS="kafka:9092" \
                    -e MAIL_PASSWORD="$MAIL_PASSWORD" \
                    notification-service
                '''
            }
        }

    }
}