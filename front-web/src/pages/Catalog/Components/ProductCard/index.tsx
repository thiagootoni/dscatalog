import React from 'react';
import {ReactComponent as ProductImage} from "../../../../core/assets/images/product.svg";
import "./styles.scss";

const ProductCard = () =>(
    <div className="card-base bd-radius-10 product-card">
        <div className="product-img">
            <ProductImage />
        </div>
        <hr/>
        <div className="product-info">            
            <p>Computador Desktop - Intel Core i7</p>
        </div>
        <div className="product-price">
            <span className="product-currency">R$</span>
            <h3>2560,00</h3>
        </div>
    </div>
);

export default ProductCard;