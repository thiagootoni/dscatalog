import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { ReactComponent as ArrowIcon} from '../../../../core/assets/images/arrow.svg';
import ProductPrice from '../../../../core/components/ProductPrice';
import { Product} from '../../../../core/types/Product';
import makeRequest from '../../../../core/utils/request';
import "./styles.scss";

type ParamsType = {
    productId: string;
}

const ProductDetails = () =>{

    const { productId } = useParams<ParamsType>();

    const[product, setProduct] = useState<Product>();

    useEffect(() => {

        makeRequest({url: `/products/${productId}`})
        .then(Response => setProduct(Response.data));

    }, [productId]);

    return(        
        <div className="product-details-container card-base bd-radius-20">
           
            <Link to="/products" className="go-back-container">
                <ArrowIcon className="arrow-icon"/>
                <h1 className="text-go-back">voltar</h1>
            </Link>

            {
                product && (

                <div className="row">
                    <div className="col-6 pr-5">
                        <div className = "card-product-details bd-radius-20 text-center">
                            <img src={product?.imgUrl} alt={product?.name} className="product-details-image"/>
                        </div>

                        <div className="product-details-info-container">
                            <h1 className="product-name">
                               {product?.name}
                            </h1>
                            <ProductPrice price={product?.price} />
                        </div>
                    </div>
                
                    <div className="col-6 card-product-details bd-radius-20">                    
                        <h1 className="text-title-description">Descrição do Produto</h1>
                        <p className="text-description">
                            {
                                product?.description
                            }
                        </p>                                      
                    </div>
                </div>

                )
            }

        </div>
    );   
};

export default ProductDetails;