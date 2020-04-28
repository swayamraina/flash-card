import React from 'react';

import Tag from '../components/Tag'



function TagsContainer(props) {

	return (
		<div>
			{ props.tags.map( (tag, index) => <Tag key={tag} text={tag} index={index} onClose={props.onClose} /> ) }
		</div>
	);
	
}

export default TagsContainer;