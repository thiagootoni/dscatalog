import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { ReactComponent as ArrowIcon} from 'core/assets/images/arrow.svg';
import ProductPrice from 'core/components/ProductPrice';
import { Product} from 'core/types/Product';
import makeRequest from 'core/utils/request';
import ProductDescriptionLoader from '../Loaders/ProductDescriptionLoader';
import ProductInfoLoader from '../Loaders/ProductInfoLoader';
import "./styles.scss";

type ParamsType = {
    productId: string;
}

const ProductDetails = () =>{

    const { productId } = useParams<ParamsType>();

    const[product, setProduct] = useState<Product>();
    const [isLoading, setIsLoading] = useState<Boolean>(false);

    useEffect(() => {

        setIsLoading(true);
        makeRequest({url: `/products/${productId}`})
        .then(Response => setProduct(Response.data))
        .finally( () => setIsLoading(false));

    }, [productId]);

    return(        
        <div className="product-details-container card-base bd-radius-20">
           
            <Link to="/products" className="go-back-container">
                <ArrowIcon className="arrow-icon"/>
                <h1 className="text-go-back">voltar</h1>
            </Link>


                <div className="row">
                    <div className="col-6 pr-5">

                        {
                            isLoading ? <ProductInfoLoader /> :

                            <>
                                <div className = "card-product-details bd-radius-20 text-center">
                                    <img src={product?.imgUrl} alt={product?.name} className="product-details-image"/>
                                </div>

                                <div className="product-details-info-container">
                                    <h1 className="product-name">
                                        {product?.name}
                                    </h1>
                                    { product && <ProductPrice price={product?.price} /> }
                                </div>
                            </>
                        }
                        
                    </div>
                
                    <div className="col-6 card-product-details bd-radius-20">

                        {
                            isLoading ? <ProductDescriptionLoader /> :

                            <>
                                <h1 className="text-title-description">Descrição do Produto</h1>
                                <p className="text-description">
                                    {
                                        product?.description
                                    }
                                </p>
                            </>                            
                        }                                                              
                    </div>
                    
                </div>
        </div>
    );   
};

export default ProductDetails;