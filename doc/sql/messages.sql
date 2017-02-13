CREATE OR REPLACE FUNCTION insertorupdatemessage(key_p character varying, data_p character varying)
  RETURNS void AS
$BODY$
BEGIN
     LOOP
         UPDATE bundlekeyvalue SET value = data_p WHERE key = key_p;
         IF found THEN
             RETURN;
         END IF;

         BEGIN
             INSERT INTO bundlekeyvalue(key,value) VALUES (key_p, data_p);
             RETURN;
         EXCEPTION WHEN unique_violation THEN
         END;
     END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

SELECT insertorupdatemessage('PARAM_SECONDSEXPIRESRESTREGULARSESSION', '3600');
SELECT insertorupdatemessage('PARAM_GOOGLE_APIKEY', 'reelbook-157618');
SELECT insertorupdatemessage('PARAM_GOOGLE_CLIENTID', '822657132611-le5ivjjco3upqr3hbqstestj6q2ip2fs.apps.googleusercontent.com');
SELECT insertorupdatemessage('PARAM_GOOGLE_SECRET', 'Q2osaQv70mZZpo5hFgL3wU1z');