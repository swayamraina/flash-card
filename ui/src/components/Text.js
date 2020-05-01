import React from 'react';

import '../styles/Text.css'


function Text(props) {

	return (
		<span className="tag-text">
			{props.text}
		</span>
	);
	
}

export default Text;