import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Voyageur from './voyageur';
import Billet from './billet';
import Voyage from './voyage';
import Colis from './colis';
import Client from './client';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}voyageur`} component={Voyageur} />
      <ErrorBoundaryRoute path={`${match.url}billet`} component={Billet} />
      <ErrorBoundaryRoute path={`${match.url}voyage`} component={Voyage} />
      <ErrorBoundaryRoute path={`${match.url}colis`} component={Colis} />
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
