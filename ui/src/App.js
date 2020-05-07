import React, { useState } from 'react';
import Container from './containers/Container'



function App () {

  const [ loading, setLoading ] = useState(false)
  const [ success, setSuccess ] = useState(-1)

  return (
    <div>
        <Container 
            loading={loading} setLoading={setLoading} 
            success={success} setSuccess={setSuccess} 
        />
    </div>
  );

}

export default App;
