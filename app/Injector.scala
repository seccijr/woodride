import repositories._
import models._
import controllers._
import services.{ProductService, TProductServiceComposition}
import factories._

object Injector {
  lazy val injLocationRepository: TLocationRepository = new LocationRepository

  lazy val injLocationModel: TLocationModel = new LocationModel with TLocationRepositoryComposition {
    val locationRepository = injLocationRepository
  }

  lazy val injLotRepository: TLotRepository =  new LotRepository with TLotFactoryComposition {
    val recurLotRepository = this
    val lotModel = new LotModel with TLotRepositoryComposition {
      val lotRepository = recurLotRepository
    }
    val lotFactory = new LotFactory(injLocationModel, lotModel)
  }

  lazy val injStockModel: TStockModel = new StockModel with TLotRepositoryComposition with TStockFactoryComposition {
    val lotRepository = injLotRepository
    val stockFactory = new StockFactory
  }

  lazy val injProductRepository: TProductRepository = new ProductRepository with TProductFactoryComposition {
    val productFactory = new ProductFactory(injStockModel)
  }

  lazy val injProductModel: TProductModel = new ProductModel with TProductRepositoryComposition {
    val productRepository = injProductRepository
  }

  lazy val applicationController: TApplicationController = new ApplicationController with TProductServiceComposition {
    val productService = new ProductService with TProductModelComposition {
      val productModel = injProductModel
    }
  }

  lazy val productController: TProductController = new ProductController with TProductServiceComposition {
    val productService = new ProductService with TProductModelComposition {
      val productModel = injProductModel
    }
  }
}
