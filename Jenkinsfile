pipeline {
    agent {
        kubernetes {
            label 'seedPT'
            defaultContainer 'maven'
            yaml """
    apiVersion: v1
    kind: Pod
    metadata:
      name: cloudbees-core
    spec:
      containers:
        - name: gradle
          image: gradle
          runAsUser: 1000
          command:
            - cat
          tty: true
          workingDir: "/home/jenkins/agent"
          securityContext:
            runAsUser: 1000
       """
        }
    }
    stages {
        stage('SeedPT') {
            steps {
                container('gradle') {
                        jobDsl targets: ['Seed.groovy'].join('\n'),
                                removedJobAction: 'DELETE',
                                removedViewAction: 'DELETE',
                                lookupStrategy: 'SEED_JOB'
                    }

            }
        }
    }
}
