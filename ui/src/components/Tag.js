import React from 'react';

import Text from './Text'
import Close from './Close'



function Tag(props) {

	return (
		<div>
			<Text text={props.text} />
			<Close index={props.index} onClose={props.onClose} />
		</div>
	);
	
}

export default Tag;