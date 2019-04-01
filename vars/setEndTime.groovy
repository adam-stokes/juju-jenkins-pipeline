/* Sets endtime
 *
 */
def call() {
    def collect_debug_sh = "${utils.cipy} infra/collect-debug.py"
    sh "cd jobs && ${collect_debug_sh} endtime"
}
