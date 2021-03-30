import React from 'react';
import { BrowserRouter, Switch, Route} from 'react-router-dom';
import Navbar from './core/components/Navbar';
import Admin from './pages/Admin';
import Catalog from './pages/Catalog';
import Home from './pages/Home';

const Routes = () =>(
    <BrowserRouter>

        <Navbar />
        
        <Switch>
            <Route path = "/" exact>
                <Home />
            </Route>
        </Switch>

        <Switch>
            <Route path = "/catalog" >
                <Catalog />
            </Route>
        </Switch> 

        <Switch>
            <Route path = "/admin" >
                <Admin />
            </Route>
        </Switch> 

    </BrowserRouter>
);

export default Routes;