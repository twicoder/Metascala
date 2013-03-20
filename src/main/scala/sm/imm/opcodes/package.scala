package sm.imm

import sm.VmThread
import sm.virt

/**
 * `opcodes` contains the stack manipulating behavior of each individual
 * opcode. Each opcode is a case class or case object extending the trait
 * [[sm.imm.OpCode]]. These are split into three separate files to help keep
 * compile times down.
 *
 * A large number of the opcodes are unused (they extend [[sm.imm.opcodes.UnusedOpCode]])
 * as ASM folds these into other opcodes for us automatically. For example,
 * `LDC`, `LDC_W` and `LDC_2W` all get folded into `LDC` before being given to us
 */
package object opcodes{

  type B = virt.Byte
  type C = virt.Char
  type I = virt.Int
  type J = virt.Long
  type F = virt.Float
  type D = virt.Double
  type S = virt.Short
  type Z = virt.Boolean
  type L = virt.Obj

  private[opcodes] case class UnusedOpCode(val id: Byte, val insnName: String) extends OpCode{
    def op = ctx => ???
  }
  implicit def intToByte(n: Int) = n.toByte
  implicit class poppable(val vt: VmThread) extends AnyVal{
    def pop = vt.frame.stack.pop()
    def push(x: virt.StackVal) = vt.frame.stack.push(x)
  }
  private[opcodes] abstract class BaseOpCode(val id: Byte, val insnName: String) extends OpCode{
    def op: VmThread => Unit
  }

}
