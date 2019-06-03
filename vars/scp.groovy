// cp's a file to remote

def call(hostname, src, dst) {
    sh "scp -i /var/lib/jenkins/.ssh/cdkbot_rsa -oStrictHostKeyChecking=no ${src} ${hostname}:${dst}"
}
