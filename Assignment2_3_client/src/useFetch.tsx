import { useState, useEffect } from 'react';

const useFetch = (url: string) => {
  const [data, setData] = useState<Object>({});
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const abortCont = new AbortController();

    setTimeout(() => {
      fetch(url, { signal: abortCont.signal })
      .then(res => {
        if (!res.ok) { // error coming back from server
          throw Error('could not fetch the data for that resource\nStatus:' + res.status + " " + res.statusText + "\n" + res.body) ;
        } 
        return res.json();
      })
      .then(data => {
        setIsPending(false);
        setData(data);
        setError("");
      })
      .catch(err => {
      
        if (err.name === 'AbortError') {
          console.log('fetch aborted')
        } else {
          // auto catches network / connection error
          setIsPending(false);
          setError(err.message);
        }
      })
    }, 1000);

    // abort the fetch
    return () => abortCont.abort();
  }, [url])

  return { data, isPending, error };
}
 
export default useFetch;