// cp's a file to remote

def call(ipaddr, src, dst) {
    sh "scp -i /var/lib/jenkins/.ssh/cdkbot_rsa -oStrictHostKeyChecking=no ${src} ubuntu@${ipaddr}:${dst}"
}
