pipeline {
    agent {
        kubernetes {
            label 'seedPT'
            defaultContainer 'maven'
            yaml """
    apiVersion: v1
    kind: Pod
    metadata:
      labels:
        some-label: seedPT
    spec:
      containers:
      - name: maven-one
        image: maven:3.3.9-jdk-8-alpine
        command:
        - cat
        tty: true    
       """
        }
    }
    stages {
        stage('SeedPT') {
            steps {
                container('maven') {
                        jobDsl targets: ['Seed.groovy'].join('\n'),
                                removedJobAction: 'DELETE',
                                removedViewAction: 'DELETE',
                                lookupStrategy: 'SEED_JOB'
                    }

            }
        }
    }
}
