pipeline{

	agent any
	
	tools{
        maven "Maven"
        }
	
	stages{	
		stage('Build'){
			steps{
				git branch: 'main',url:'https://github.com/prakritshrivastava/PageObjectmodelTestNGHybridFW.git'
				bat "mvn -Dmaven.test.failure.ignore=true clean package"
			}
			post{
				success{
					junit '**/target/surefire-reports/TEST-*.xml'
					archiveArtifacts 'target/*.jar'
				}
			}
		}
		
		stage("Run Unit Test"){
			steps{
				echo("run UT")
			}
		}
	
		stage("Run Integration Test"){
			steps{
				echo("run IT")
			}
		}
	
		stage("Deploy to dev"){
			steps{
				echo("Deploy to dev")
			}
		}
	
		stage("Deploy to Stage"){
			steps{
				echo("Deploy to Stage")
			}
		}
	
		stage("Deploy to QA"){
			steps{
				echo("Deploy to QA")
			}
		}
		
		stage('Regression Tests'){
			steps{
				catchError(buildResult:'SUCCESS',stageResult:'FAILURE'){
					cleanWs()
					git branch: 'main',url:'https://github.com/prakritshrivastava/PageObjectmodelTestNGHybridFW.git'
					bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/TestRunners/TestNG_Regression.xml -Denv=qa"
				}
			}
		}
		
		stage('Publish Allure Reports') {
				steps {
				script {
				allure( [
				includeProperties: false,
				jdk: '',
				properties: [],
				reportBuildPolicy: 'ALWAYS',
				results: [[path: '/allure-results']]])
				}
			}
		}
		
		stage('Publish ChainTest HTML Report'){
				steps{
					publishHTML( [allowMissing: false,
					alwaysLinkToLastBuild: false,
					keepAll: true,
					reportDir: 'target/chaintest',
					reportFiles: 'Index.html',
					reportName: 'HTML Regression ChainTest Report',
					reportTitles: ''])
					}
				}
		
		stage("Deploy to PROD"){
			steps{
				echo("Deploy to PROD")
			}
		}
	}

}