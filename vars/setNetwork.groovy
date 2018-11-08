/* Sets the bridge networks to correct mtu, pain in the ass but has to be done
 */

def call() {
    sh "sudo ip link set dev docker0 mtu 1458 || true"
    sh "sudo ip link set dev lxdbr0 mtu 1458 || true"
    sh "sudo ip link set dev lxdbr10 mtu 1458 || true"
}
