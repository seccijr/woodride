import repo._
import models._
import controllers._
import services.{ProductService, TProductServiceComposition}
import factories.{TProductFactoryComposition, TLotFactoryComposition, LotFactory, ProductFactory}

object Injector {
  lazy val injLocationRepository = new LocationRepository

  lazy val injLotRepository =  new LotRepository with TLotFactoryComposition {
    val lotFactory = new LotFactory with TLocationModelComposition {
      val locationModel = new LocationModel with TLocationRepositoryComposition {
        val locationRepository = injLocationRepository
      }
    }
  }

  lazy val injProductRepository = new ProductRepository with TProductFactoryComposition {
    val productFactory = new ProductFactory with TStockModelComposition {
      val stockModel = new StockModel with TLotRepositoryComposition {
        val lotRepository = injLotRepository
      }
    }
  }

  lazy val applicationController: TApplicationController = new ApplicationController
  lazy val productController: TProductController = new ProductController with TProductServiceComposition {
    val productService = new ProductService with TProductModelComposition {
      val productModel = new ProductModel with TProductRepositoryComposition {
        val productRepository = injProductRepository
      }
    }
  }
}
