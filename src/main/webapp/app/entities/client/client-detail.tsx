import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetail = (props: IClientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="sengpApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="firstname">
              <Translate contentKey="sengpApp.client.firstname">Firstname</Translate>
            </span>
          </dt>
          <dd>{clientEntity.firstname}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="sengpApp.client.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{clientEntity.lastname}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="sengpApp.client.email">Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="sengpApp.client.password">Password</Translate>
            </span>
          </dt>
          <dd>{clientEntity.password}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="sengpApp.client.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{clientEntity.telephone}</dd>
          <dt>
            <span id="bornDate">
              <Translate contentKey="sengpApp.client.bornDate">Born Date</Translate>
            </span>
          </dt>
          <dd>{clientEntity.bornDate ? <TextFormat value={clientEntity.bornDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="cin">
              <Translate contentKey="sengpApp.client.cin">Cin</Translate>
            </span>
          </dt>
          <dd>{clientEntity.cin}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="sengpApp.client.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{clientEntity.photo}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetail);
