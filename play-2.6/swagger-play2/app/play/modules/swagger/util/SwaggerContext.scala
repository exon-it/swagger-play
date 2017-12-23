package play.modules.swagger.util

import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer

/**
  * @author ayush
  * @since 10/9/11 5:36 PM
  *
  */
object SwaggerContext {
  private val LOGGER = LoggerFactory.getLogger("play.modules.swagger.util.SwaggerContext")

  var suffixResponseFormat = true

  private val classLoaders = ListBuffer.empty[ClassLoader]
  registerClassLoader(this.getClass.getClassLoader)

  def registerClassLoader(cl: ClassLoader): Unit = this.classLoaders += cl

  def loadClass(name: String): Class[_] = {
    var clazz: Class[_] = null

    for (classLoader <- classLoaders.reverse) {
      if(clazz == null) {
        try {
          clazz = Class.forName(name, true, classLoader)
        } catch {
          case _: ClassNotFoundException => LOGGER.debug("Class not found in classLoader " + classLoader)
        }
      }
    }

    if(clazz == null)
      throw new ClassNotFoundException("class " + name + " not found")

    clazz
  }
}
