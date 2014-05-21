import controllers._
import models.{ProductModel, TProductModelComposition}
import services.{ProductService, TProductServiceComposition}

object Injector {
  lazy val applicationController: TApplicationController = new ApplicationController
  lazy val productController: TProductController = new ProductController with TProductServiceComposition {
    val productService = new ProductService with TProductModelComposition {
      val productModel = new ProductModel
    }
  }
  lazy val testController: TTestController = new TestController with TProductServiceComposition {
    val productService = new ProductService with TProductModelComposition {
      val productModel = new ProductModel
    }
  }
}
