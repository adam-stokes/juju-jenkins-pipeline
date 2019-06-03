// Runs a command on a remote instance

def call(hostname, cmd) {
    sh "ssh -tt -o StrictHostKeyChecking=no -o ServerAliveInterval=60 -o ServerAliveCountMax=110 -i /var/lib/jenkins/.ssh/cdkbot_rsa ${hostname} '${cmd}'"
}
