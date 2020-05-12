import React from 'react';

import Header from '../components/Header'
import InputContainer from './InputContainer';
import Success from '../components/Success'
import Failure from '../components/Failure'
import Loader from '../components/Loader'



function Container (props) {
	
	const loadingScreen = () => {
		return (
			<Loader />
		);
	}

	const saveResultScreen = () => {
		return (
			<div>
				{ props.success === 0 ? <Failure /> : <Success /> }
			</div>
		);
	}

	const inputScreen = () => {
		return (
			<div>
				<Header />
				<InputContainer loading={props.loading} setLoading={props.setLoading} setSuccess={props.setSuccess} />
			</div>
		);
	}

	const homeScreen = () => {
		return (
			<div>
				{props.success === -1 ? inputScreen() : saveResultScreen() }
			</div>
		);
	}

	return (
		<div>
			{ props.loading ? loadingScreen() : homeScreen() }
		</div>
	);
}

export default Container;