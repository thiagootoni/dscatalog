import React from 'react';
import {ReactComponent as ProductImage} from "../../../../core/assets/images/product.svg";
import ProductPrice from '../../../../core/components/ProductPrice';
import "./styles.scss";

const ProductCard = () =>(
    <div className="card-base bd-radius-10 product-card">
        <div className="product-img">
            <ProductImage className="product-size"/>
        </div>
        <hr/>
        <div className="product-info">            
            <p>Computador Desktop - Intel Core i7</p>
        </div>
        <ProductPrice price="2799,00"/>
    </div>
);

export default ProductCard;