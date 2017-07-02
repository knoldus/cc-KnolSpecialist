package com.knoldus.cc.ks.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait KnolSpecialistService extends Service{

  def getTechnique(id: Int): ServiceCall[NotUsed, Technology]

  override def descriptor: Descriptor = {
    import Service._
    named("ks")
      .withCalls(
        pathCall("/api/get/technology/:id", getTechnique _)
      )
      .withAutoAcl(true)
  }

}

case class Technology(id: Int, name: String)