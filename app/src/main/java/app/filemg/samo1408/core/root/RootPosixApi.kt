package app.filemg.samo1408.core.root

import com.topjohnwu.superuser.Shell
import app.filemg.samo1408.R
import app.filemg.samo1408.core.i18n.tr
import app.filemg.samo1408.core.props.AndroidIds
import app.filemg.samo1408.core.props.PosixApi
import app.filemg.samo1408.core.props.PosixStat
import java.io.IOException

class RootPosixApi : PosixApi {

    private fun run(command: String): Shell.Result = Shell.cmd(command).exec()

    private fun check(result: Shell.Result) {
        if (!result.isSuccess) throw IOException(result.err.firstOrNull() ?: tr(R.string.files_root_command_failed, result.code))
    }

    override fun lstat(path: String): PosixStat {
        val r = run(RootCommands.stat(path))
        val line = r.out.firstOrNull() ?: throw IOException(r.err.firstOrNull() ?: tr(R.string.files_root_stat_failed))
        return RootCommands.parseStat(line) ?: throw IOException(tr(R.string.files_root_stat_output, line))
    }

    override fun chmod(path: String, mode: Int) = check(run(RootCommands.chmod(path, mode)))

    override fun chown(path: String, uid: Int, gid: Int) = check(run(RootCommands.chown(path, uid, gid)))

    override fun readlink(path: String): String? =
        run(RootCommands.readlink(path)).takeIf { it.isSuccess }?.out?.firstOrNull()

    override fun userName(uid: Int): String = AndroidIds.name(uid)

    override fun groupName(gid: Int): String = AndroidIds.name(gid)
}
