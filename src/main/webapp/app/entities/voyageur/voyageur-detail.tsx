import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './voyageur.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVoyageurDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VoyageurDetail = (props: IVoyageurDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { voyageurEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voyageurDetailsHeading">
          <Translate contentKey="sengpApp.voyageur.detail.title">Voyageur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.id}</dd>
          <dt>
            <span id="firstname">
              <Translate contentKey="sengpApp.voyageur.firstname">Firstname</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.firstname}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="sengpApp.voyageur.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.lastname}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="sengpApp.voyageur.email">Email</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="sengpApp.voyageur.password">Password</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.password}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="sengpApp.voyageur.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.telephone}</dd>
          <dt>
            <span id="bornDate">
              <Translate contentKey="sengpApp.voyageur.bornDate">Born Date</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.bornDate ? <TextFormat value={voyageurEntity.bornDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="cin">
              <Translate contentKey="sengpApp.voyageur.cin">Cin</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.cin}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="sengpApp.voyageur.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{voyageurEntity.photo}</dd>
        </dl>
        <Button tag={Link} to="/voyageur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voyageur/${voyageurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ voyageur }: IRootState) => ({
  voyageurEntity: voyageur.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VoyageurDetail);
