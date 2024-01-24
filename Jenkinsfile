pipeline {
   agent any
   
   stages {
     stage('Checkout') {
	steps {
	   checkout scm
	   }
	}

	stage('Build and Push Docker Image') {
	   steps {
	     script {
		def gitTag = sh(returnStdout: true, script: 'git describe --tags --always').trim()
		def dockerImage = docker.build("tkdtls/dev-quiz-backend:${gitTag}", "./")
                withDockerRegistry([ credentialsId: "dockerHubCredentialsId", url: "https://index.docker.io/v1/" ]) {
		   dockerImage.push("${gitTag}")
		}
	     }
	   }
	}
   }
}
