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
                withCredentials([usernamePassword(
                    credentialsId: 'db-remoteuser',
                    usernameVariable: 'DB_USERNAME',
                    passwordVariable: 'DB_PASSWORD'
                )]) {
                    sh '''
                    docker stop notification-service || true
                    docker rm notification-service || true

                    docker run -d \
                    --name notification-service \
                    --network backend_default \
                    --restart unless-stopped \
                    -p 8093:8443 \
                    -e DB_URL="jdbc:sqlserver://sqlserver:1433;databaseName=jobportal;trustServerCertificate=true" \
                    -e DB_USERNAME="$DB_USERNAME" \
                    -e DB_PASSWORD="$DB_PASSWORD" \
                    -e App_ACTIVATION_URL=https://192.168.31.184:8090/activate/activateAccount \
                    -e KAFKA_BOOTSTRAP_SERVERS="kafka:9092" \
                    notification-service
                '''
            }
        }

    }
}