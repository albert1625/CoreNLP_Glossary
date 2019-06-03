@echo started...

for %%f in (*) do (
	dot -Tsvg %%f -o terms_img/%%~nf.svg
)

@echo done