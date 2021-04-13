import React from 'react';
import ProductPrice from 'core/components/ProductPrice';
import { Product } from 'core/types/Product';
import "./styles.scss";

type Props = {
    product : Product
}

const ProductCard = ({product}: Props) =>(
    
    <div className="card-base bd-radius-10 product-card text-center">
        <img src={product.imgUrl} alt={product.name} className="product-img"/>
        <hr/>
        <div className="product-info">            
            <p>{product.name}</p>
        </div>
        <ProductPrice price={product.price}/>
    </div>
);

export default ProductCard;