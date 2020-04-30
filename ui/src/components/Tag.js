import React from 'react';

import Text from './Text'
import Close from './Close'
import Separator from './Separator'

import '../styles/Tag.css'



function Tag(props) {

	return (
		<div>
			<ul className="tag-item" >
				<Text text={props.text} />
				<Separator />
				<Close index={props.index} onClose={props.onClose} />
			</ul>
		</div>
	);
	
}

export default Tag;