import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ProductsResponse } from "core/types/Product";
import makeRequest from "core/utils/request";
import ProductCard from "./Components/ProductCard";
import ProductCardLoader from "./Components/Loaders/ProductCardLoader";
import "./styles.scss";
import Pagination from "core/components/Pagination";

const Catalog = () => {
  const [productsResponse, setProductsResponse] = useState<ProductsResponse>();
  const [isLoading, setIsLoading] = useState<Boolean>(false);
  const [activePage, setActivePage] = useState<number>(0);

  useEffect(() => {
    const params = {
      page: activePage,
      linesPerPage: 10,
    };

    setIsLoading(true);
    makeRequest({ url: "/products", params })
      .then((response) => setProductsResponse(response.data))
      .finally(() => setIsLoading(false));
  }, [activePage]);

  return (
    <div className="catalog-container">
      <h1 className="catalog-title">Catálogo de produtos</h1>

      <div className="catalog-products">
        {isLoading ? (
          <ProductCardLoader repeat={4} />
        ) : (
          productsResponse?.content.map((product) => (
            <Link to={`/products/${product.id}`} key={product.id}>
              <ProductCard product={product} />
            </Link>
          ))
        )}
      </div>

      {productsResponse && (
        <Pagination 
            totalPages={productsResponse?.totalPages} 
            activePage={activePage}
            changePage={page => setActivePage(page)}
        />
      )}
    </div>
  );
};

export default Catalog;
