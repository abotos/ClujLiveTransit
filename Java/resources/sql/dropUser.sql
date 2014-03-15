begin execute immediate 'drop user ${db.user} cascade'; exception when others then if sqlcode != -1918 then raise; end if; end;
/