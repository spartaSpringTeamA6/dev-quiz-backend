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
                withDockerRegistry([ credentialsId: "docker-access" ]) {
		   dockerImage.push("${gitTag}")
		   dockerImage.push("latest")
		}
	     }
	   }
	}

	stage('Start') {
	   steps {
		echo "여러분 반갑습니다!"
	   }
	}
	
   }

   post {
	success {
		discordSend (
			description: "성공!",
			webhookURL: credentials('discord_notify')
		)
	}

	failure {
		discordSend (
			description: "실패 ㅠ",
			webhookURL: credentials('discord_notify')
		)
	}
  }
}

 
